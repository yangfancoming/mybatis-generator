
package org.mybatis.generator.internal;

import org.mybatis.generator.api.ProgressCallback;

/**
 * This class implements a progress callback that does nothing. It is used when
 * the client passes in a null for the ProgressCallback.
 * 
 * @author Jeff Butler
 */
public class NullProgressCallback implements ProgressCallback {

    public NullProgressCallback() {
        super();
    }

    @Override
    public void generationStarted(int totalTasks) {
        // nothing to do in the null callback
    }

    @Override
    public void introspectionStarted(int totalTasks) {
        // nothing to do in the null callback
    }

    @Override
    public void saveStarted(int totalTasks) {
        // nothing to do in the null callback
    }

    @Override
    public void startTask(String taskName) {
        // nothing to do in the null callback
    }

    @Override
    public void checkCancel() throws InterruptedException {
        // nothing to do in the null callback
    }

    @Override
    public void done() {
        // nothing to do in the null callback
    }
}
