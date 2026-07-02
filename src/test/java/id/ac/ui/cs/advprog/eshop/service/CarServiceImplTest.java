package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CarServiceImplTest {
    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(new CarRepository());
    }

    @Test
    void testCreateAndFindAll() {
        Car car = createCar("car-1", "Toyota Avanza", "Black", 2);

        Car savedCar = carService.create(car);
        List<Car> cars = carService.findAll();

        assertEquals(car, savedCar);
        assertEquals(1, cars.size());
        assertEquals("Toyota Avanza", cars.getFirst().getCarName());
    }

    @Test
    void testFindById() {
        carService.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        Car foundCar = carService.findById("car-1");

        assertEquals("Toyota Avanza", foundCar.getCarName());
    }

    @Test
    void testUpdate() {
        carService.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        carService.update("car-1", createCar("car-1", "Toyota Innova", "White", 4));

        assertEquals("Toyota Innova", carService.findById("car-1").getCarName());
        assertEquals(4, carService.findById("car-1").getCarQuantity());
    }

    @Test
    void testDeleteById() {
        carService.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        carService.deleteCarById("car-1");

        assertNull(carService.findById("car-1"));
    }

    private Car createCar(String id, String name, String color, int quantity) {
        Car car = new Car();
        car.setCarId(id);
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }
}
