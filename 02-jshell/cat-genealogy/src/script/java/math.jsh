import java.math.*;
import static java.math.RoundingMode.*;

MathContext LOTS = new MathContext(100, HALF_UP);
MathContext mc = LOTS;

BigDecimal $ans = BigDecimal.ZERO;
String mode = "r";

BigDecimal fromNumber(Number num) {
  return new BigDecimal(String.valueOf(num.doubleValue()));
}

BigDecimal fromBigInteger(BigInteger num) {
  return new BigDecimal(num, mc);
}

BigDecimal bd(Object obj) {
  if ( obj instanceof BigInteger ) {
    return fromBigInteger((BigInteger)obj);
  }

  if ( obj instanceof Number ) {
    return fromNumber((Number)obj);
  }

  if ( obj instanceof BigDecimal ) {
    return (BigDecimal)obj;
  }

  try {
    if ( obj instanceof String ) {
      return fromNumber(new Double((String)obj));
    }
  } catch ( Exception e ) {
    throw new IllegalArgumentException("Can't convert '" + obj + "' into a number");
  }

  throw new IllegalArgumentException("Can't convert " + obj.getClass() + " into a number");
}

BigDecimal rt(Object num) {
  $ans = bd(num).sqrt(mc);
  return $ans;
}

BigDecimal sq(Object num) {
  $ans = bd(num).pow(2, mc);
  return $ans;
}

BigDecimal add(Object left, Object right) {
  $ans = bd(left).add(bd(right), mc);
  return $ans;
}

BigDecimal sub(Object left, Object right) {
  $ans = bd(left).subtract(bd(right), mc);
  return $ans;
}

BigDecimal mul(Object left, Object right) {
  $ans = bd(left).multiply(bd(right), mc);
  return $ans;
}

BigDecimal div(Object left, Object right) {
  $ans = bd(left).divide(bd(right), mc);
  return $ans;
}

BigDecimal sin(Object obj) {
  if ( "r".equals(mode) ) {
    $ans = bd(Math.sin(bd(obj).doubleValue()));
  } else {
    $ans = bd(Math.sin(bd(obj).doubleValue() * Math.PI / 180));
  }
  return $ans;
}

BigDecimal cos(Object obj) {
  if ( "r".equals(mode) ) {
    $ans = bd(Math.cos(bd(obj).doubleValue()));
  } else {
    $ans = bd(Math.cos(bd(obj).doubleValue()));
  }
  return $ans;
}

BigDecimal tan(Object obj) {
  if ( "r".equals(mode) ) {
    $ans = bd(Math.tan(bd(obj).doubleValue()));
  } else {
    $ans = bd(Math.tan(bd(obj).doubleValue()));
  }
  return $ans;
}

//-- ans

BigDecimal sqa() {
  return sq($ans);
}

BigDecimal rta() {
  return rt($ans);
}

BigDecimal adda(Object right) {
  return add($ans, right);
}

BigDecimal suba(Object right) {
  return sub($ans, right);
}

BigDecimal mula(Object right) {
  return mul($ans, right);
}

BigDecimal diva(Object right) {
  return div($ans, right);
}

BigDecimal sina() {
  return sin($ans);
}

BigDecimal cosa() {
  return cos($ans);
}

BigDecimal tana() {
  return tan($ans);
}
