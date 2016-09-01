package subscription2.predicate;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/31/2016
 * Time: 12:21 PM
 */
public class ChangeTypeEvaluatorTest {

    @Test
    public void testEvaluateOldFalseNewFalse() throws Exception {
        Assert.assertEquals(ChangeType.NO_CHANGE, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, false, false));
    }

    @Test
    public void testEvaluateOldFalseNewTrue() throws Exception {
        Assert.assertEquals(ChangeType.UPSERT, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, false, true));
    }

    @Test
    public void testEvaluateOldTrueNewFalse() throws Exception {
        Assert.assertEquals(ChangeType.DELETE, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, true, false));
    }

    @Test
    public void testEvaluateOldTrueNewTrue() throws Exception {
        Assert.assertEquals(ChangeType.UPSERT, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, true, true));
    }

    @Test
    public void testEvaluateOldNullNewFalse() throws Exception {
        Assert.assertEquals(ChangeType.NO_CHANGE, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, null, false));
    }

    @Test
    public void testEvaluateOldNullNewTrue() throws Exception {
        Assert.assertEquals(ChangeType.UPSERT, ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, null, true));
    }

    @Test
    public void testEvaluateOldFalseNewNull() throws Exception {
        try {
            ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, false, null);
        } catch (IllegalArgumentException e) {
            // expected exception - pass the test
            return;
        }
        Assert.fail();
    }

    @Test
    public void testEvaluateOldTrueNewNull() throws Exception {
        try {
            ChangeTypeEvaluator.evaluate(aBoolean -> aBoolean, true, null);
        } catch (IllegalArgumentException e) {
            // expected exception - pass the test
            return;
        }
        Assert.fail();
    }

    @Test
    public void testEvaluateOldNullNewNull() throws Exception {
        try {
            ChangeTypeEvaluator.<Boolean>evaluate(aBoolean -> aBoolean, null, null);
        } catch (IllegalArgumentException e) {
            // expected exception - pass the test
            return;
        }
        Assert.fail();
    }
}