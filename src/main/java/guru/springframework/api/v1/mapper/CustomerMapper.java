package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

@Mapper
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    
    @Mapping(target = "customerUrl", ignore = true)
    CustomerDTO customerToCustomerDto(Customer customer);
    
    @Mapping(target = "id", ignore = true)
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
