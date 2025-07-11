package com.crmapp.controller;

import com.crmapp.entity.Lead;
import com.crmapp.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    @Autowired
    private LeadRepository leadRepo;

    @GetMapping
    public List<Lead> getAll() {
        return leadRepo.findAll();
    }

    @PostMapping
    public Lead add(@RequestBody Lead lead) {
        return leadRepo.save(lead);
    }

    @PutMapping("/{id}")
    public Lead update(@PathVariable Long id, @RequestBody Lead leadDetails) {
        Lead lead = leadRepo.findById(id).orElseThrow();
        lead.setStatus(leadDetails.getStatus());
        lead.setNotes(leadDetails.getNotes());
        return leadRepo.save(lead);
    }
}
