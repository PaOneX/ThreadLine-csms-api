package edu.icet.mapper;

import edu.icet.model.dto.InvoiceDto;
import edu.icet.model.dto.InvoiceRequestDto;
import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoiceDate", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Invoice toEntity(InvoiceRequestDto requestDto);

    @Mapping(target = "orderId", source = "order.id")
    InvoiceDto toDto(Invoice invoice);

    List<InvoiceDto> toDtoList(List<Invoice> entities);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(InvoiceRequestDto dto, @MappingTarget Invoice entity);

    default Order mapOrder(Long orderId) {
        if (orderId == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderId);
        return order;
    }
}
