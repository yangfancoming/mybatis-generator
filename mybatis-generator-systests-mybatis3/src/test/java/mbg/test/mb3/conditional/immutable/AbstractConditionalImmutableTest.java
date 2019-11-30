
package mbg.test.mb3.conditional.immutable;

import mbg.test.mb3.AbstractTest;

public abstract class AbstractConditionalImmutableTest extends AbstractTest {

    @Override
    public String getMyBatisConfigFile() {
        return "mbg/test/mb3/conditional/immutable/MapperConfig.xml";
    }
}
