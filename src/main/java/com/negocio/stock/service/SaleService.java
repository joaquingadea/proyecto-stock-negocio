package com.negocio.stock.service;

import com.negocio.stock.dto.*;
import com.negocio.stock.model.Product;
import com.negocio.stock.model.Sale;
import com.negocio.stock.model.SaleDetail;
import com.negocio.stock.model.Seller;
import com.negocio.stock.repository.IProductRepository;
import com.negocio.stock.repository.ISaleRepository;
import com.negocio.stock.repository.ISellerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ISellerRepository sellerRepository;

    /* - El proceso crea una nueva venta, inicializa el total de la venta en 0, busca el vendedor a traves de su ID en la base de datos
    *  - Valiida uno por uno los SaleDetails y en caso de que algo falle se hace un rollback a traves de la annotation @Transactional
    *  - Una vez validado todo lo necesario se terminan de settear los atributos necesarios para la nueva venta y se da el alta de la misma en la BD
    */
    @Override
    public MessageResponseDTO create(@Valid CreateSaleRequestDTO request,String username) {

        Seller seller = sellerRepository.findByUserUsername(username)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontro el vendedor: "+ username));

        BigDecimal totalSale = BigDecimal.ZERO;

        Sale newSale = new Sale();

        validateNoDuplicateProducts(request);

        for (SaleDetailRequestDTO dto : request.details()){

            Product product = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro el producto con id: "+ dto.productId()));

            if((product.getStock() - dto.quantity()) < 0){
                throw new IllegalArgumentException("Se intenta vender más productos de los posibles");
            }
            if (product.getPrice() == null) {
                throw new IllegalStateException("Producto sin precio: " + product.getName());
            }

            // ACTUALIZAR STOCK DE CADA PRODUCTO
            product.setStock(product.getStock() - dto.quantity());

            SaleDetail saleDetail = new SaleDetail();

            saleDetail.setSale(newSale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(dto.quantity());
            saleDetail.setUnitPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(dto.quantity()));

            saleDetail.setItemTotal(itemTotal);

            totalSale = totalSale.add(saleDetail.getItemTotal());
            newSale.getTicket().add(saleDetail);
        }
        newSale.setDate(LocalDateTime.now());
        newSale.setTotal(totalSale);
        newSale.setSeller(seller);

        saleRepository.save(newSale);

        return new MessageResponseDTO("Successfully saved sale.");
    }

    @Override
    public Page<GetSaleResponseDTO> getAllSales(Pageable pageRequest) {
        return saleRepository.findAll(pageRequest).map(sale -> new GetSaleResponseDTO(
                sale.getId(),
                sale.getSeller().getUser().getUsername(),
                sale.getTotal(),
                sale.getDate(),
                sale.getTicket()
                        .stream()
                        .map(saleDetail -> new GetSaleDetailResponseDTO(
                            saleDetail.getProduct().getName(),
                                saleDetail.getQuantity(),
                                saleDetail.getUnitPrice(),
                                saleDetail.getItemTotal()
                        )).toList()
        ));
    }

    @Override
    public Page<GetSaleResponseDTO> getMySales(String username, Pageable pageRequest) {
        return saleRepository.findAllBySellerUserUsername(username,pageRequest).map(sale -> new GetSaleResponseDTO(
                sale.getId(),
                sale.getSeller().getUser().getUsername(),
                sale.getTotal(),
                sale.getDate(),
                sale.getTicket()
                        .stream()
                        .map(saleDetail -> new GetSaleDetailResponseDTO(
                                saleDetail.getProduct().getName(),
                                saleDetail.getQuantity(),
                                saleDetail.getUnitPrice(),
                                saleDetail.getItemTotal()
                            )
                        ).toList()
        ));
    }

    public void validateNoDuplicateProducts(CreateSaleRequestDTO request){
        Set<Long> ids = request.details()
                .stream()
                .map(SaleDetailRequestDTO::productId)
                .collect(Collectors.toSet());

        if (ids.size() != request.details().size()) {
            throw new IllegalArgumentException("Hay productos repetidos en la venta");
        }
    }
}
