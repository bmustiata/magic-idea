package com.germaniumhq.magic_group.model;

@lombok.Getter
@lombok.Setter
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
public class LineReference implements TreeItem {
    String expression;
    String description;
    String longDescription;

    @Override
    public String getName() {
        return expression;
    }

    @Override
    public String toString() {
        return "LineReference{" +
                "expression='" + expression + '\'' +
                '}';
    }
}
