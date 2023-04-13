package com.ecoway.api.model.mapper;

import com.ecoway.api.controller.CategoryController;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.model.dto.VehicleDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class VehicleHateoasMapper {

    public static EntityModel<VehicleDTO> linksVehicle(VehicleDTO vehicleDTO) {
        Link categoryLink = WebMvcLinkBuilder.linkTo(CategoryController.class)
                .slash("public/category/byname?name="+ vehicleDTO.getCategoryName())
                .withRel("Category");
        return EntityModel.of(vehicleDTO, categoryLink);
    }
}
