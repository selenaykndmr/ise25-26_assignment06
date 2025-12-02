package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class UsersSystemTests extends AbstractSysTest {

    //TODO: Uncomment once user endpoint is implemented

    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).getFirst());


        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    //TODO: Add at least two additional tests for user operations

    @Test
    void deleteUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();
        var createdDto = userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).getFirst();

        userRequests.delete(createdDto.id());

        assertNotNull(createdDto.id());
    }

    @Test
    void getAllUsers() {
        var users = userRequests.getAll();

        assertNotNull(users);

    }

}