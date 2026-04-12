package com.negocio.stock.service;

import com.negocio.stock.dto.SellerResponseDTO;
import com.negocio.stock.model.Seller;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.repository.ISaleRepository;
import com.negocio.stock.repository.ISellerRepository;
import com.negocio.stock.repository.IUserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SellerService implements ISellerService{

    @Autowired
    private ISellerRepository sellerRepository;
    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserSecRepository userRepository;
    @Autowired
    private IUserSecService userService;

    @Override
    public Page<SellerResponseDTO> getSellers(PageRequest pageRequest) {

        return sellerRepository.findSellersWithUserOrAdmin(pageRequest)
                .map(seller -> new SellerResponseDTO(
                            seller.getId(),
                            seller.getUser().getUsername(),
                            saleRepository.countBySellerId(seller.getId()),
                            userRepository.findRolesByUserId(seller.getUser().getId())
        ));
    }
    @Override
    public void applyForSeller(Long sellerId) {
        Seller sellerRepo = sellerRepository.findById(sellerId).orElseThrow();
        sellerRepo.setSellerRequested(true);
        sellerRepository.save(sellerRepo);
    }

    @Override
    public void denySellerRequest(Long sellerId) {
        Seller sellerRepo = sellerRepository.findById(sellerId).orElseThrow();
        sellerRepo.setSellerRequested(false);
        sellerRepository.save(sellerRepo);
    }

    @Override
    public void acceptSellerRequest(Long sellerId) {
        Seller sellerRepo = sellerRepository.findById(sellerId).orElseThrow();
        Long userId = sellerRepo.getUser().getId();
        userService.setRole(userId,"USER");
        userService.removeRole(userId,"GUEST");
        sellerRepo.setSellerApproved(true);
        sellerRepository.save(sellerRepo);
    }

    @Override
    public boolean isSellerRequested(Long sellerId) {
        return sellerRepository.isSellerRequestedById(sellerId);
    }


}
