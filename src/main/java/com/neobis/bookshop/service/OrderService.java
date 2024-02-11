package com.neobis.bookshop.service;


import com.neobis.bookshop.dtos.BookDto;
import com.neobis.bookshop.dtos.CreateOrderDto;
import com.neobis.bookshop.dtos.OrderDto;
import com.neobis.bookshop.dtos.OrderItemDto;
import com.neobis.bookshop.entities.Book;
import com.neobis.bookshop.entities.Order;
import com.neobis.bookshop.entities.OrderItem;
import com.neobis.bookshop.entities.User;
import com.neobis.bookshop.enums.OrderStatus;
import com.neobis.bookshop.exceptions.*;
import com.neobis.bookshop.mapper.BookMapper;
import com.neobis.bookshop.mapper.OrderMapper;
import com.neobis.bookshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final BookMapper bookMapper;



    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.convertToDtoList(orders);
    }

    public OrderDto getOrderById(Long orderId) {
        Order order =  orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found."));
        return orderMapper.convertToDto(order);
    }

    public void cancelCustomersOrder(Long customerId, Long orderId) {
        Order order =  orderRepository.findByUserIdAndId(customerId, orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found."));
        if(order.getStatus() == OrderStatus.DELIVERED){
            throw new UnableToCancelException("Order is already delivered");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


    public List<OrderDto> getAllOrdersByCustomerId(Long userId) {
        try {
            List<Order> orders = orderRepository.findAllByUserId(userId);
            return orderMapper.convertToDtoList(orders);
        } catch (Exception e) {
            throw new AccountDoesNotExistException("Customer with id: " + userId + " not found");
        }

    }




    public OrderDto createOrder(Long userId, CreateOrderDto dto) {
        User user = userService.getCustomerById(userId);
        if (user == null) {
            throw new AccountDoesNotExistException("User not found with ID: " + userId);
        }
        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(user.getAddress());
        for (OrderItemDto orderItemDto : dto.getItems()) {
            Book book = bookService.getBookById(orderItemDto.getBookId());
            if (book == null) {
                throw new BookDoesNotExistException("Book not found with ID: " + orderItemDto.getBookId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(orderItemDto.getQuantity());
            updateBookStock(orderItemDto, book);
            order.getOrderItems().add(orderItem);
        }
        calculateTotalPrice(order);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return orderMapper.maptoOrderDto(user.getId(), order);
    }


    public void calculateTotalPrice(Order order) {
        BigDecimal sum = BigDecimal.valueOf(0);
        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                BigDecimal itemPrice = orderItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                sum = sum.add(itemPrice);
            }
        }
        order.setTotalPrice(sum);
    }





    private void updateBookStock(OrderItemDto orderItem, Book  book) {

        int quantityToSubtract = orderItem.getQuantity();

        if (book.getStockQuantity() < quantityToSubtract) {
            throw new OutOfStockException("Order quantity is more than available stock");
        }

        int updatedStock = book.getStockQuantity() - quantityToSubtract;
        book.setStockQuantity(updatedStock);

        BookDto bookDto = bookMapper.convertToDto(book);
        bookService.updateBook(bookDto, book.getId());

    }

    public OrderDto getOrder(Long userId, Long orderId) {
        return orderMapper.convertToDto(orderRepository
                .findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found.")));
    }

    public void cancelOrder(Long orderId) {
        Order order =  orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found."));
        if(order.getStatus() == OrderStatus.DELIVERED){
            throw new UnableToCancelException("Order is already delivered");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}