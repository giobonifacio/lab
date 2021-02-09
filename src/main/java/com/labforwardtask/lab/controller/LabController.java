package com.labforwardtask.lab.controller;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.dto.LabResponse;
import com.labforwardtask.lab.service.LabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/lab/")
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping(path = "/category")
    public ResponseEntity<LabResponse> getAllCategories() {
        try {
            return ResponseEntity.ok(new LabResponse(labService.findAllCategories()));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new LabResponse(e.getMessage(), e));
        }
    }

    @PostMapping(path = "/category")
    public ResponseEntity<LabResponse> insertCategory(@RequestBody CategoryDTO category) {
        try {
            return ResponseEntity.status(CREATED).body(new LabResponse(labService.insertCategory(category)));
        } catch (Exception e) {
            return ResponseEntity.status(PRECONDITION_FAILED).body(new LabResponse(e.getMessage(), e));
        }
    }

    @GetMapping(path = "/category/{categoryId}/item")
    public ResponseEntity<LabResponse> getItemsFromCategory(@PathVariable String categoryId) {
        try {
            CategoryDTO category = labService.findCategoryById(categoryId);
            if (nonNull(category)) {
                return ResponseEntity.ok(new LabResponse(category.getItems()));
            }

            return ResponseEntity.status(NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new LabResponse(e.getMessage(), e));
        }
    }

    @PostMapping(path = "/item")
    public ResponseEntity<LabResponse> insertItem(@RequestBody ItemDTO item) {
        try {
            return ResponseEntity.status(CREATED).body(new LabResponse(labService.insertItem(item)));
        } catch (Exception e) {
            return ResponseEntity.status(PRECONDITION_FAILED).body(new LabResponse(e.getMessage(), e));
        }
    }

    @PostMapping(path = "/item/{id}")
    public ResponseEntity<LabResponse> updateItem(@RequestBody ItemDTO item, @PathVariable String id) {
        try {
            return ResponseEntity.ok().body(new LabResponse(labService.updateItem(item, id)));
        } catch (Exception e) {
            return ResponseEntity.status(PRECONDITION_FAILED).body(new LabResponse(e.getMessage(), e));
        }
    }

    @DeleteMapping(path = "/category")
    public ResponseEntity<LabResponse> eraseAll() {
        try {
            labService.eraseAll();
            return  ResponseEntity.ok().body(new LabResponse("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new LabResponse(e.getMessage(), e));
        }
    }
}
