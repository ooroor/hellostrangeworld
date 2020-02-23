package net.barakiroth.hellostrangeworld.frontend.consumer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InitialPartDo {
  @Getter(AccessLevel.PUBLIC)
  private String initialPart;
}