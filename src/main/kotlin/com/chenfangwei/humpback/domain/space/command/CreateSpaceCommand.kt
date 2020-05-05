package com.chenfangwei.humpback.domain.space.command

import javax.validation.constraints.NotNull

class CreateSpaceBody(@field:NotNull val name: String)

data class CreateSpaceCommand(var name: String, var userID: String)