package com.neobis.bookshop.controller.rest.adminController;

import com.neobis.bookshop.dtos.AccountDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Admin Users",
        description = "Admin control panel for users."
)
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User has not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User has role of CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final OrderService orderService;


    @Operation(
            summary = "Retrieve all Customers",
            description = "Get list of Customer dto from database"
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/get-all")
    public ResponseEntity<List<AccountDto>> getAllCustomers() {
        return ResponseEntity.ok(userService.getAllActiveCustomers());
    }

    @Operation(
            summary = "Retrieve Customer",
            description = "Get a Customer dto from database specified by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found.")

    })
    @GetMapping("/{userId}")
    public ResponseEntity<AccountDto> getOneCustomer(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getCustomerDtoById(userId));
    }

    @Operation(
            summary = "Delete Customer",
            description = "Delete Customer object from database. Only changes the status to deleted"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found."),
            @ApiResponse(responseCode = "403", description = "Only ADMIN has access to DELETE")
    })

    @PutMapping("/delete/{userId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long userId) {
        userService.deleteCustomerById(userId);
        return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Customer's Orders",
            description = "Get pageable list of Order dto of one Customer specified by customerId"
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found")
    @Parameter(name = "userId", description = "Customer's unique identifier")
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDto>> getAllOrdersByCustomerId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(userId));
    }





}