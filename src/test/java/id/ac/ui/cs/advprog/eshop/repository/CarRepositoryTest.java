package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFindAll() {
        Car car = createCar("car-1", "Toyota Avanza", "Black", 2);

        Car savedCar = carRepository.create(car);
        Iterator<Car> carIterator = carRepository.findAll();

        assertEquals(car, savedCar);
        assertTrue(carIterator.hasNext());
        assertEquals("Toyota Avanza", carIterator.next().getCarName());
    }

    @Test
    void testCreateWithNullIdGeneratesId() {
        Car car = createCar(null, "Honda Brio", "Red", 3);

        Car savedCar = carRepository.create(car);

        assertNotNull(savedCar.getCarId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();

        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        carRepository.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        Car foundCar = carRepository.findById("car-1");

        assertNotNull(foundCar);
        assertEquals("Toyota Avanza", foundCar.getCarName());
    }

    @Test
    void testFindByIdNotFound() {
        carRepository.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        Car foundCar = carRepository.findById("missing-car");

        assertNull(foundCar);
    }

    @Test
    void testUpdateFound() {
        carRepository.create(createCar("car-1", "Toyota Avanza", "Black", 2));
        Car updatedCar = createCar("car-1", "Toyota Innova", "White", 4);

        Car result = carRepository.update("car-1", updatedCar);

        assertNotNull(result);
        assertEquals("Toyota Innova", result.getCarName());
        assertEquals("White", result.getCarColor());
        assertEquals(4, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = createCar("missing-car", "Toyota Innova", "White", 4);

        Car result = carRepository.update("missing-car", updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteById() {
        carRepository.create(createCar("car-1", "Toyota Avanza", "Black", 2));

        carRepository.delete("car-1");

        assertNull(carRepository.findById("car-1"));
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
