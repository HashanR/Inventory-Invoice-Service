package com.virtusa.inventory.invoice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.inventory.invoice.model.Discount;
import com.virtusa.inventory.invoice.service.DiscountService;

@RestController
@RequestMapping(value = "/imscloud")
public class DiscountConroller {
	
	@Autowired
	private DiscountService discountService;
	
	@RequestMapping(value = "/discount", method = RequestMethod.GET)
	public ResponseEntity<Optional<Discount>> fetchOne(@PathVariable Integer id) {
		
		Optional<Discount> optional =  discountService.findOne(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional);
		}else {
			return null;
		}
		
	}
	
	@RequestMapping(value = "/discount", method = RequestMethod.POST)
	public ResponseEntity<Discount> save(@RequestBody Discount discount) {
		
		boolean matched = false;

		List<Discount> discounts = discountService.fetchAll();
		for (Discount item : discounts) {
			if (item.getPriceRange().equals(discount.getPriceRange())) {
				matched = true;
				break;
			} else {
				matched = false;
			}
		}

		if (discount.getPriceRange() != null && matched == false) {
		
			Discount newDiscount = new Discount();
			newDiscount.setId(discount.getId());
			newDiscount.setDiscount(discount.getDiscount());
			newDiscount.setPriceRange(discount.getPriceRange());
			return ResponseEntity.ok(discountService.save(newDiscount));

		} else {
			
			return null;
		}
	}

}
