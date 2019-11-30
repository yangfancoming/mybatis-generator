
package org.mybatis.generator.api;

import org.mybatis.generator.internal.NullProgressCallback;

/**
 * A slightly more verbose progress callback.
 * 
 * @author Jeff Butler
 * 
 */
public class VerboseProgressCallback extends NullProgressCallback {

    public VerboseProgressCallback() {
        super();
    }

    @Override
    public void startTask(String taskName) {
        System.out.println(taskName);
    }
}
