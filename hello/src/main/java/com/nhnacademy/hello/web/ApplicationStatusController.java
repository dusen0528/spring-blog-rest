/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.hello.web;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.nhnacademy.hello.actuator.ApplicationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 27/06/2023
 */

@RestController
@RequestMapping("/actuator/status")
public class ApplicationStatusController {

    private final ApplicationInfoManager applicationInfoManager;
    private final ApplicationStatus applicationStatus;

    public ApplicationStatusController(ApplicationInfoManager applicationInfoManager, ApplicationStatus applicationStatus) {
        this.applicationInfoManager = applicationInfoManager;
        this.applicationStatus = applicationStatus;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void stopStatus(){
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        applicationStatus.stopService();
    }

}
