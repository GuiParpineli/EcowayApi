package com.ecoway.api;

import com.ecoway.api.model.*;
import com.ecoway.api.repository.*;
import com.ecoway.api.security.SystemUserRoles;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class SeedDB implements ApplicationRunner {
    private final CategoryRepository categoryRepository;
    private final VehicleRepository vehicleRepository;
    private final CityRepository cityRepository;
    private final FeatureRepository featureRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final VehicleModelRepository vehicleModelRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public SeedDB(CategoryRepository categoryRepository, VehicleRepository vehicleRepository, CityRepository cityRepository, FeatureRepository featureRepository, ImageRepository imageRepository, UserRepository userRepository, BookingRepository bookingRepository, VehicleModelRepository vehicleModelRepository) {
        this.categoryRepository = categoryRepository;
        this.vehicleRepository = vehicleRepository;
        this.cityRepository = cityRepository;
        this.featureRepository = featureRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        bCryptPasswordEncoder = new BCryptPasswordEncoder();
       /* if (imageRepository.findAll().isEmpty()) {
            imageRepository.save(
                    Image.builder()
                            .id("208")
                            .url("https://garagem360.com.br/wp-content/uploads/2023/01/Peugeot-208-Griffe-2023-1.jpg")
                            .title("Pegout-208")
                            .build()
            );
            imageRepository.save(
                    Image.builder()
                            .id("500e")
                            .url("https://500e.fiat.com.br/content/dam/fiat/products/332/312/0/2022/page/hero.png")
                            .title("Fiat-500e")
                            .build()
            );
            imageRepository.save(
                    Image.builder()
                            .id("Q8")
                            .url("https://platform.cstatic-images.com/xlarge/in/v2/stock_photos/7737d0e4-6afa-4cec-82ed-4e9a24d5d739/c1d5fe85-8cb3-48c9-978a-21c39bfd6943.png")
                            .title("Audio-Q8")
                            .build()
            );
            imageRepository.save(
                    Image.builder()
                            .id("Master")
                            .url("https://img1.icarros.com/dbimg/galeriaimgmodelo/8/124334_1.jpg")
                            .title("Renault-Master")
                            .build()
            );
            imageRepository.save(
                    Image.builder()
                            .id("JS21")
                            .url("https://www.jacmotors.com.br/public/media/veiculos/1643236234-branco.png")
                            .title("E-JS21")
                            .build()
            );
            imageRepository.save(
                    Image.builder()
                            .id("TSX")
                            .url("https://motoshopsa.ch/wp-content/uploads/2020/11/TSX-1.png")
                            .title("SuperSoco-TSX")
                            .build()
            );
        }

        if (cityRepository.findAll().isEmpty()) {

            cityRepository.save(
                    City.builder()
                            .id("batatais")
                            .cityName("Batatais")
                            .state("SP")
                            .country("BR")
                            .build()
            );

            cityRepository.save(
                    City.builder()
                            .id("cascavel")
                            .cityName("Cascavel")
                            .state("SC")
                            .country("BR")
                            .build()
            );

            cityRepository.save(
                    City.builder()
                            .id("recife")
                            .cityName("Recife")
                            .state("PE")
                            .country("BR")
                            .build()
            );
            cityRepository.save(
                    City.builder()
                            .id("ribeirao-preto")
                            .cityName("Ribeirão Preto")
                            .state("SP")
                            .country("BR")
                            .build()
            );
        }

        if (featureRepository.findAll().isEmpty()) {

            featureRepository.save(
                    Feature.builder()
                            .id("condicionado")
                            .name("Ar Condicionado")
                            .icon("snowflake")
                            .build()
            );
            featureRepository.save(
                    Feature.builder()
                            .id("airBag")
                            .name("Air Bag")
                            .icon("air-bag")
                            .build()
            );
            featureRepository.save(
                    Feature.builder()
                            .id("abs")
                            .name("ABS")
                            .icon("car")
                            .build()
            );

        }

        if (vehicleModelRepository.findAll().isEmpty()) {

            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("500e")
                            .brand("Fiat")
                            .model("500e")
                            .image(imageRepository.findByTitle("Fiat-500e"))
                            .build()
            );

            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("e208")
                            .brand("Pegeout")
                            .model("E-208")
                            .image(imageRepository.findByTitle("Pegout-208"))
                            .build()
            );

            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("ejs21")
                            .brand("JAC")
                            .model("E-JS21")
                            .image(imageRepository.findByTitle("E-JS21"))
                            .build()
            );

            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("q8")
                            .brand("Audio")
                            .model("Q8")
                            .image(imageRepository.findByTitle("Audio-Q8"))
                            .build()
            );

            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("master")
                            .brand("Renault")
                            .model("Master")
                            .image(imageRepository.findByTitle("Renault-Master"))
                            .build()
            );
            vehicleModelRepository.save(
                    VehicleModel.builder()
                            .id("tsx")
                            .brand("SuperSoco")
                            .model("TSX")
                            .image(imageRepository.findByTitle("SuperSoco-TSX"))
                            .build()
            );

        }

        if (categoryRepository.findAll().isEmpty()) {

            categoryRepository.save(
                    Category.builder()
                            .id("compacto")
                            .name("Compacto")
                            .description("Simples e economico")
                            .basePrice(150.000)
                            .autonomyAverage(500)
                            .rechargeTimeAverage(4)
                            .feature(
                                    List.of(featureRepository.findAll().get(0))
                            )
                            .vehicles(
                                    List.of(vehicleModelRepository.findVehicleModelByModel("E-208")
                                    )
                            )
                            .build());

            categoryRepository.save(
                    Category.builder()
                            .id("basico")
                            .name("Basico")
                            .description("Teste")
                            .basePrice(250.000)
                            .autonomyAverage(400)
                            .rechargeTimeAverage(2)
                            .feature(
                                    List.of(featureRepository.findAll().get(0),
                                            featureRepository.findAll().get(1))
                            )
                            .vehicles(
                                    List.of(vehicleModelRepository.findVehicleModelByModel("500e"),
                                            vehicleModelRepository.findVehicleModelByModel("E-JS21")
                                    ))
                            .build());

            categoryRepository.save(
                    Category.builder()
                            .id("carga_leve")
                            .name("Carga Leve")
                            .description("Feito pra entregas rapidas e ecologicas")
                            .basePrice(350.000)
                            .autonomyAverage(600)
                            .rechargeTimeAverage(3)
                            .feature(
                                    List.of(featureRepository.findAll().get(2))
                            )
                            .vehicles(
                                    List.of(vehicleModelRepository.findVehicleModelByModel("Master"))
                            )
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .id("sport")
                            .name("Sport")
                            .description("Feito pra entregas rapidas e ecologicas")
                            .basePrice(550.000)
                            .autonomyAverage(200)
                            .rechargeTimeAverage(3)
                            .feature(
                                    List.of(featureRepository.findAll().get(2))
                            )
                            .vehicles(
                                    List.of(vehicleModelRepository.findVehicleModelByModel("Q8"))
                            )
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .id("moto")
                            .name("Moto")
                            .description("Feito pra entregas rapidas e ecologicas")
                            .basePrice(350.000)
                            .autonomyAverage(1000)
                            .rechargeTimeAverage(3)
                            .vehicles(
                                    List.of(vehicleModelRepository.findVehicleModelByModel("TSX"))
                            )
                            .build()
            );
        }

        if (vehicleRepository.findAll().isEmpty()) {

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("ZAY105C6")
                            .color("cinza")
                            .model(vehicleModelRepository.findVehicleModelByModel("500e"))
                            .category(categoryRepository.findByVehicleModel("500e").get())
                            .city(cityRepository.findById("batatais").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("RLU71J0")
                            .color("Azul")
                            .model(vehicleModelRepository.findVehicleModelByModel("E-208"))
                            .category(categoryRepository.findByVehicleModel("E-208").get())
                            .city(cityRepository.findById("batatais").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("HLU71J0")
                            .color("Prata")
                            .model(vehicleModelRepository.findVehicleModelByModel("E-208"))
                            .category(categoryRepository.findByVehicleModel("E-208").get())
                            .city(cityRepository.findById("batatais").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("OGU89O7")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("500e"))
                            .category(categoryRepository.findByVehicleModel("500e").get())
                            .city(cityRepository.findById("ribeirao-preto").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("POGU89O7")
                            .color("Vermelho")
                            .model(vehicleModelRepository.findVehicleModelByModel("500e"))
                            .category(categoryRepository.findByVehicleModel("500e").get())
                            .city(cityRepository.findById("ribeirao-preto").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("WOGU89O7")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("Q8"))
                            .category(categoryRepository.findByVehicleModel("Q8").get())
                            .city(cityRepository.findById("ribeirao-preto").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("XOGU89O7")
                            .color("Preto")
                            .model(vehicleModelRepository.findVehicleModelByModel("TSX"))
                            .category(categoryRepository.findByVehicleModel("TSX").get())
                            .city(cityRepository.findById("ribeirao-preto").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("NGN1010D6")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("E-JS21"))
                            .category(categoryRepository.findByVehicleModel("E-JS21").get())
                            .city(cityRepository.findById("recife").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("MGNX9010D6")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("E-208"))
                            .category(categoryRepository.findByVehicleModel("E-208").get())
                            .city(cityRepository.findById("recife").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("OIHN1010D6")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("Master"))
                            .category(categoryRepository.findByVehicleModel("Master").get())
                            .city(cityRepository.findById("recife").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("LIHN1012D6")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("Master"))
                            .category(categoryRepository.findByVehicleModel("Master").get())
                            .city(cityRepository.findById("cascavel").get())
                            .build());

            vehicleRepository.save(
                    Vehicle.builder()
                            .id("JIHN10122D6")
                            .color("Branco")
                            .model(vehicleModelRepository.findVehicleModelByModel("Master"))
                            .category(categoryRepository.findByVehicleModel("Master").get())
                            .city(cityRepository.findById("cascavel").get())
                            .build());
        }*/

        if (userRepository.findAll().isEmpty()){
            userRepository.save(
                    SystemUser.builder()
                            .name("admin")
                            .lastname("admilson")
                            .email("admin@email.com")
                            .password(bCryptPasswordEncoder.encode("123456"))
                            .phone("16454124624")
                            .systemUserRoles(SystemUserRoles.ROLE_ADMIN)
                            .build()
            );
        }

      /*  if (bookingRepository.findAll().isEmpty()) {
            bookingRepository.save(
                    Booking.builder()
                            .checkInDay(LocalDate.now())
                            .checkInTime(LocalTime.now())
                            .checkOutDay(LocalDate.now())
                            .checkOutTime(LocalTime.now())
                            .status(true)
                            .user(userRepository.findAll().get(0))
                            .city(cityRepository.findAll().get(0))
                            .category(categoryRepository.findAll().get(0))
                            .build()
            );
            bookingRepository.save(
                    Booking.builder()
                            .checkInDay(LocalDate.now())
                            .checkInTime(LocalTime.now())
                            .checkOutDay(LocalDate.now())
                            .checkOutTime(LocalTime.now())
                            .status(true)
                            .user(userRepository.findAll().get(0))
                            .city(cityRepository.findAll().get(0))
                            .category(categoryRepository.findAll().get(0))
                            .build()
            );
        }*/

    }
}
