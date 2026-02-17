package com.negocio.stock.service;

import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.SaleDetailRequestDTO;
import com.negocio.stock.dto.CreateSaleRequestDTO;
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
    public MessageResponseDTO create(@Valid CreateSaleRequestDTO request) {

        Seller seller = sellerRepository.findById(request.sellerId())
                            .orElseThrow(() -> new EntityNotFoundException("No se encontro el vendedor con id: "+ request.sellerId()));

        BigDecimal totalSale = BigDecimal.ZERO;

        Sale newSale = new Sale();

        for (SaleDetailRequestDTO dto : request.details()){

            Product product = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro el producto con id: "+ dto.productId()));

            if((product.getStock() - dto.quantity()) < 0){
                throw new IllegalArgumentException("Se intenta vender más productos de los posibles");
            }

            SaleDetail saleDetail = new SaleDetail();

            saleDetail.setSale(newSale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(dto.quantity());
            saleDetail.setUnitPrice(product.getPrice());

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
    public Page<Sale> getAllSales(Pageable pageRequest) {
        return saleRepository.findAll(pageRequest);
    }

}
