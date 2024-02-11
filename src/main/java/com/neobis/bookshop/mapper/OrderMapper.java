package com.neobis.bookshop.mapper;

import com.neobis.bookshop.dtos.OrderDto;
import com.neobis.bookshop.entities.Order;
import com.neobis.bookshop.entities.OrderItem;
import com.neobis.bookshop.entities.User;
import com.neobis.bookshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class OrderMapper {



    private final ModelMapper modelMapper;
    private final UserService userService;

    public OrderMapper(ModelMapper modelMapper, UserService userService) {

        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public List<OrderDto> convertToDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<Order> convertToEntityList(List<OrderDto> orderDtos) {
        return orderDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }


    public OrderDto maptoOrderDto(Long userId, Order order){
        OrderDto orderDto = new OrderDto();
        User user = userService.getCustomerById(userId);
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderedPerson(user.getFirstName()+ " " + user.getLastName());
        orderDto.setDeliveryAddress(user.getAddress());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalPrice(order.getTotalPrice());

        for(OrderItem orderItem : order.getOrderItems()){
            orderDto.getOrderedBooks().put(orderItem.getBook().getTitle(), orderItem.getQuantity().toString());
        }
        return orderDto;
    }


}