package com.sysc.workshop.cart.service;

import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.model.CartItem;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.cart.repository.CartItemRepository;
import com.sysc.workshop.cart.repository.CartRepository;
import com.sysc.workshop.product.service.product.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private  final IProductService iProductService;
    private final ICartService iCartService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(UUID cartId, UUID productId, int quantity) {
        // if similar item is added just update the quantity

        Cart cart = iCartService.getCart(cartId);
        Product product= iProductService.getProductEntityById(productId);
        //checks if cart items already exist
        // if yes, return null
        // if no, create a new one
        CartItem cartItem = cart.getCartItems().stream().
                filter(item-> item.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());


        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemToCart(UUID cartId, UUID productId) {
        Cart cart = iCartService.getCart(cartId);
        CartItem cartItem = getCartItem(productId, cart);
        cart.removeItem(cartItem);
        cartRepository.save(cart);

    }
    @Override
    public CartItem getCartItem(UUID productId, Cart cart) {
        return cart.getCartItems().stream().
                filter(item -> item.getProduct().
                        getId().equals(productId)).findFirst().orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void updateItemQuantity(UUID cartId, UUID productId, int quantity) {
        Cart cart = iCartService.getCart(cartId);
        cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().
                ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();

                });
        cart.updateTotalAmount();
        cartRepository.save(cart);

    }
}