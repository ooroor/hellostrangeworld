package net.barakiroth.hellostrangeworld.backend.consumer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ModifierDo {
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int    id;
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private String modifier;
}
