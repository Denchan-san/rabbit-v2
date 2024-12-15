package org.example.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import org.example.model.Role;

@ResourceProperties(path = "roles")
public interface RoleResource extends PanacheEntityResource<Role, Long> {
}
