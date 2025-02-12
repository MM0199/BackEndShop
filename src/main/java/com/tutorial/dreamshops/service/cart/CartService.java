package com.tutorial.dreamshops.service.cart;

import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.Cart;
import com.tutorial.dreamshops.model.User;
import com.tutorial.dreamshops.repository.CartItemRepository;
import com.tutorial.dreamshops.repository.CartRepository;
import com.tutorial.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        cartRepository
                .findById(id).ifPresentOrElse(
                        cart -> {
                            cart.getCartItems().clear();
                            cart.setTotalAmount(BigDecimal.ZERO);
                            cartRepository.save(cart);
                        },
                        () -> { throw new ResourceNotFoundException("Cart not found!"); }
                );
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        user.setCart(newCart);
        userRepository.save(user);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
