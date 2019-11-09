package net.barakiroth.hellostrangeworld.farbackend.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GreetingDescription {
  @Getter(AccessLevel.PUBLIC)
  public int    id;
  @Getter(AccessLevel.PUBLIC)
  public String adjective;
}
