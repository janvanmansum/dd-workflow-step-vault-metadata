/*
 * Copyright (C) 2021 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.wf.vaultmd.resources;

import nl.knaw.dans.lib.dataverse.DataverseClient;
import nl.knaw.dans.wf.vaultmd.api.StepInvocation;
import nl.knaw.dans.wf.vaultmd.core.DataverseService;
import nl.knaw.dans.wf.vaultmd.core.SetVaultMetadataTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.Executor;

@Path("/invoke")
@Produces(MediaType.APPLICATION_JSON)
public class StepInvocationResource {

    private static final Logger log = LoggerFactory.getLogger(StepInvocationResource.class);

    private final Executor executor;
    private final DataverseService dataverseService;

    public StepInvocationResource(Executor executor, DataverseService dataverseService) {
        this.executor = executor;
        this.dataverseService = dataverseService;
    }

    @POST
    public void run(@Valid StepInvocation inv) {
        log.info("Received invocation: {}", inv);
        executor.execute(new SetVaultMetadataTask(inv, dataverseService));
        log.info("Added new task to queue");
    }

}
