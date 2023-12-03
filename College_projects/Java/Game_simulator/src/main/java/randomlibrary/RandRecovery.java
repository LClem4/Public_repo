package randomlibrary;

import java.util.List;

import recovery.RecoveryBehavior;
import recovery.RecoveryFractional;
import recovery.RecoveryLinear;
import recovery.RecoveryNone;

public class RandRecovery implements Random<RecoveryBehavior> {
  List<RecoveryBehavior> choices = List.of(new RecoveryNone(), new RecoveryLinear(
      new RandInt(1,10).choose()), new RecoveryFractional(new RandInt(1,10).choose()));
  
  public RecoveryBehavior choose() { 
    return new FromList<RecoveryBehavior>(choices).choose(); 
  }
}
