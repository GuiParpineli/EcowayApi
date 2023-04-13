package com.ecoway.api.repository;

import com.ecoway.api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByTitle(String title);
}
