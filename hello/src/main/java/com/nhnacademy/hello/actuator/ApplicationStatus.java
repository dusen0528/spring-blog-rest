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

package com.nhnacademy.hello.actuator;

import org.springframework.stereotype.Component;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 27/06/2023
 */
@Component
public final class ApplicationStatus {
    private boolean status = true;

    public void stopService(){
        this.status = false;
    }

    public boolean getStatus(){
        return status;
    }
}
