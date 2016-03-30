package com.tsystems.javaschool.milkroad.controller.rest;

import com.tsystems.javaschool.milkroad.controller.rest.dto.IncomeDTO;
import com.tsystems.javaschool.milkroad.controller.rest.dto.TopCustomerDTO;
import com.tsystems.javaschool.milkroad.controller.rest.dto.TopProductDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergey on 30.03.2016.
 */
@RestController
@RequestMapping("/rest")
public class StatisticsRestController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/topCustomers")
    public ResponseEntity<Set<TopCustomerDTO>> topCustomerDTOs() {
        final Map<UserDTO, BigDecimal> topCustomers;
        try {
            topCustomers = statisticsService.getTopCustomers(10);
        } catch (final MilkroadServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final Set<TopCustomerDTO> topCustomerDTOs = new HashSet<>();
        for (final UserDTO userDTO : topCustomers.keySet()) {
            topCustomerDTOs.add(new TopCustomerDTO(userDTO, topCustomers.get(userDTO)));
        }
        return new ResponseEntity<>(topCustomerDTOs, HttpStatus.OK);
    }

    @RequestMapping("/topProducts")
    public ResponseEntity<Set<TopProductDTO>> topProductDTOs() {
        final Map<ProductDTO, Integer> topProducts;
        try {
            topProducts = statisticsService.getTopProducts(10);
        } catch (final MilkroadServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final Set<TopProductDTO> topProductDTOs = new HashSet<>();
        for (final ProductDTO productDTO : topProducts.keySet()) {
            topProductDTOs.add(new TopProductDTO(productDTO, topProducts.get(productDTO)));
        }
        return new ResponseEntity<>(topProductDTOs, HttpStatus.OK);
    }

    // TODO Return total cash by requested period
    @RequestMapping("/income")
    public ResponseEntity<IncomeDTO> incomeDTO() {
        final BigDecimal totalCash;
        final BigDecimal totalCashThisMonth;
        final BigDecimal totalCashLast7Days;
        final Calendar calendar = Calendar.getInstance();
        final long currentDay = calendar.getTime().getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        final long monthFirstDay = calendar.getTime().getTime();
        final long sevenDaysAgo = currentDay - 7 * 1000 * 60 * 60 * 24;
        try {
            totalCash = statisticsService.getTotalCash();
            totalCashThisMonth = statisticsService.getTotalCashByPeriod(new Date(monthFirstDay), new Date(currentDay));
            totalCashLast7Days = statisticsService.getTotalCashByPeriod(new Date(sevenDaysAgo), new Date(currentDay));
        } catch (final MilkroadServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new IncomeDTO(totalCash, totalCashThisMonth, totalCashLast7Days), HttpStatus.OK);
    }
}
