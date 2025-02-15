package Products;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record Product(
        String id,
        String name
) {
}
