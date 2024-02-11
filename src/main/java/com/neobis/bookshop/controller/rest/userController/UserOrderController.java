package com.neobis.bookshop.controller.rest.userController;


import com.neobis.bookshop.dtos.CreateOrderDto;
import com.neobis.bookshop.dtos.OrderDto;
import com.neobis.bookshop.service.OrderService;
import com.neobis.bookshop.service.UserService;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Orders", description = "Controller for order objects")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User is not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not CUSTOMER or accessing not own data", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/orders")
public class UserOrderController {
    private final UserService userService;
    private final OrderService orderService;

    @Operation(
            summary = "Get one order",
            description = "Customer gets an order specified by id number"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique identifier of Order object")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(Authentication authentication, @PathVariable Long orderId) {
        OrderDto order = orderService.getOrder(userService.getUserIdFromAuthToken(authentication), orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Cancel order",
            description = "Customer cancels an order specified by id number. Delete mapping used"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order successfully canceled"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique identifier of Order object")

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(Authentication authentication, @PathVariable Long orderId) {
        orderService.cancelCustomersOrder(userService.getUserIdFromAuthToken(authentication), orderId);
        return new ResponseEntity<>("Order successfully canceled", HttpStatus.OK);
    }

    @Operation(
            summary = "Create Order",
            description = "Create a complex Order object with contact in it, to order book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "406", description = "Ordered quantity exceeds available stock",
                    content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(Authentication authentication,
                                                @Validated @RequestBody CreateOrderDto dto) {
        return new ResponseEntity<>(orderService.createOrder(userService.getUserIdFromAuthToken(authentication), dto),
                HttpStatus.CREATED);
    }


}