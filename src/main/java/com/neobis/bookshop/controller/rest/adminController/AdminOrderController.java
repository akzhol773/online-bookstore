package com.neobis.bookshop.controller.rest.adminController;


import com.neobis.bookshop.dtos.OrderDto;
import com.neobis.bookshop.service.OrderService;
import com.neobis.bookshop.util.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Admin Orders",
        description = "Admin control panel for orders. ADMIN has full access"
)
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User has not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User has role of CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;


    @Operation(
            summary = "Retrieve all Orders"
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(
            summary = "Retrieve one Order",
            description = "Get one Order dto from database specified by order's unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique Order object identifier")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order successfully canceled"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only ADMIN has access for PUT")
    })
    @Parameter(name = "orderId", description = "Unique Order object identifier")
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>("Order successfully canceled", HttpStatus.OK);
    }


}