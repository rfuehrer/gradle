/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.configuration.internal;

import org.gradle.api.Action;
import org.gradle.internal.DisplayName;
import org.gradle.internal.service.scopes.Scopes;
import org.gradle.internal.service.scopes.ServiceScope;

import javax.annotation.Nullable;

/**
 * Assigns and stores an ID for the application of some user code (e.g. scripts and plugins).
 */
@ServiceScope(Scopes.BuildSession)
public interface UserCodeApplicationContext {
    /**
     * Applies some user code, assigning an ID for this particular application.
     *
     * @param displayName A display name for the user code.
     * @param action The action to run to apply the user code.
     */
    void apply(DisplayName displayName, Action<? super UserCodeApplicationId> action);

    void reapply(UserCodeApplicationId id, Runnable runnable);

    <T> Action<T> decorateWithCurrent(Action<T> action);

    @Nullable
    UserCodeApplicationId current();

    @Nullable
    DisplayName currentDisplayName();
}
