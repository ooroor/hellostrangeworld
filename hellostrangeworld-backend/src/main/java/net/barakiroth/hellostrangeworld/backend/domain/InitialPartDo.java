package net.barakiroth.hellostrangeworld.backend.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InitialPartDo {
  @Getter(AccessLevel.PUBLIC)
  public String initialPart;
}
