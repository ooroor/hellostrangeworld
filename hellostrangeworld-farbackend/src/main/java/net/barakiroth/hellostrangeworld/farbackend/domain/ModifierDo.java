package net.barakiroth.hellostrangeworld.farbackend.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public final class ModifierDo {
  @Getter(AccessLevel.PUBLIC)
  public int    id;
  @Getter(AccessLevel.PUBLIC)
  public String modifier;
}
