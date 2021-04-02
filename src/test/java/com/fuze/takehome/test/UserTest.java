package com.fuze.takehome.test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.*;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends AbstractEntityTest {

    @Inject
    private UserService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    /**
     * Test the User Entity and User Service.
     * <p>
     * Assert that entity validation is working correctly and
     * the service methods are doing what they are supposed to be doing.
     * <p>
     * Before this test executes, AbstractEntityTest takes care of initializing
     * Spring and starting an in-memory DB instance. DummyDataGenerator is
     * invoked to insert a couple dummy entities into the database.
     */
    User validUser;
    User invalidUser;
    User addedUser;
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void init() {
        invalidUser = new User()
                .withActive(true)
                .withCustomerId(0L)
                .withEmail("darpan.test@gmail.com")
                .withFirstName("Darpan")
                .withLastName("Patel")
                .withMobileNumber("555-7124-554")
                .withTelephoneNumber("555-9965-12333333333333333333333333333333333")
                .withUserName("darpan_31");
        validUser = new User()
                .withActive(true)
                .withCustomerId(0L)
                .withEmail("darpan.test@gmail.com")
                .withFirstName("Darpan")
                .withLastName("Patel")
                .withMobileNumber("555-7124-554")
                .withTelephoneNumber("555-9965-123")
                .withUserName("darpan_31");
    }

    @Test
    public void testUser() {
        checkValidation();
        createUser();
        readUser();
        readAllUser();
        deleteUser();
    }


    public void checkValidation() {
        Set<ConstraintViolation<User>> constraintValidator = validator.validate(invalidUser);
        Assert.assertTrue(constraintValidator.size() > 0);
    }


    public void createUser() {
        addedUser = service.create(validUser);
        Assert.assertNotNull(addedUser.getId());
    }


    public void readUser() {
        List<User> allUsers = service.list();
        Assert.assertEquals(3, allUsers.size());

        exceptionRule.expect(NotFoundException.class);
        service.read(5L);
    }

    private boolean compareUsers(User user1, User user2) {
        return user1.isActive() == user2.isActive()
                && user1.getUserName().toString().equals(user2.getUserName().toString())
                && user1.getFirstName().toString().equals(user2.getFirstName().toString())
                && user1.getEmail().toString().equals(user2.getEmail().toString())
                && user1.getLastName().toString().equals(user2.getLastName().toString())
                && user1.getMobileNumber().toString().equals(user2.getMobileNumber().toString());
    }


    public void readAllUser() {
        List<User> allUsers = service.list();
        Assert.assertEquals(3, allUsers.size());
    }

    public void deleteUser() {
        User userAddedInDb = service.read(addedUser.getId());
        User deletedUser = service.delete(userAddedInDb.getId());
        Assert.assertTrue(compareUsers(userAddedInDb, deletedUser));
    }
}
