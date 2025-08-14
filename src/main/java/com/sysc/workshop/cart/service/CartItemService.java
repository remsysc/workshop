package com.sysc.workshop.cart.service;

import com.sysc.workshop.cart.dto.request.AddItemRequestDTO;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.model.CartItem;
import com.sysc.workshop.cart.repository.CartItemRepository;
import com.sysc.workshop.cart.repository.CartRepository;
import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.service.product.IProductService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final IProductService iProductService;
    private final ICartService iCartService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(AddItemRequestDTO requestDTO) {
        // if similar item is added just update the quantity
        UUID productId = requestDTO.getProductId();
        int quantity = requestDTO.getQuantity();
        Cart cart = iCartService.getCartByEntity(requestDTO.getCartId());
        Product product = iProductService.getProductEntityById(
            requestDTO.getProductId()
        );
        //checks if cart items already exist
        // if yes, return null
        // if no, create a new one
        CartItem cartItem = cart
            .getCartItems()
            .stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemToCart(UUID cartId, UUID productId) {
        Cart cart = iCartService.getCartByEntity(cartId);
        CartItem cartItem = getCartItem(productId, cart);
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(UUID productId, Cart cart) {
        return cart
            .getCartItems()
            .stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() ->
                new ProductNotFoundException("Product Not Found!")
            );
    }

    @Override
    public void updateItemQuantity(AddItemRequestDTO requestDTO) {
        Cart cart = iCartService.getCartByEntity(requestDTO.getCartId());
        cart
            .getCartItems()
            .stream()
            .filter(item ->
                item.getProduct().getId().equals(requestDTO.getProductId())
            )
            .findFirst()
            .ifPresent(item -> {
                item.setQuantity(requestDTO.getQuantity());
                item.setUnitPrice(item.getProduct().getPrice());
                item.setTotalPrice();
            });
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }
}
