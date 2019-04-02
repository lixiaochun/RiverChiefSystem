package assessment.service.impl;

import assessment.service.ValueCheck;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValueCheckImpl implements ValueCheck {
    @Override
    public Double valueCheck(Double a, double min, double max) {
        if (a > max) {
            return max;
        } else if (a < min) {
            return min;
        } else {
            return a;
        }
    }
}
