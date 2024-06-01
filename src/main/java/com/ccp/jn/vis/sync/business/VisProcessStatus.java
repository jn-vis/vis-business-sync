package com.ccp.jn.vis.sync.business;

import com.ccp.process.CcpProcessStatus;

public enum VisProcessStatus implements CcpProcessStatus{
	inactiveResume(400),
	inactivePosition(400),
	notAllowedRecruiter(403),
	negativatedResume(403), 
	missingFee(0), 
	missingBalance(0),
	;
	
	final int status;
	
	private VisProcessStatus(int status) {
		this.status = status;
	}

	public int status() {
		return this.status;
	}

}
