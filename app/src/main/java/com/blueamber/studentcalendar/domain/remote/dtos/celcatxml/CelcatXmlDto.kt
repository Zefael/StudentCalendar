package com.blueamber.studentcalendar.domain.remote.dtos.celcatxml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "timetable", strict = false)
data class CelcatXmlDto @JvmOverloads constructor (
    @field:ElementList(name = "event", inline = true, required = false)var event: List<EventDto> = ArrayList())

@Root(name = "event", strict = false)
class EventDto {
    @field:Attribute(name = "date", required = false) var date: String = ""
    @field:Element(name = "endtime", required = false) var endTime: String = ""
    @field:Element(name = "prettyweeks", required = false) var prettyWeeks: Int? = null
    @field:Element(name = "resources", required = false) var resources: ResourcesDto? = null
    @field:Element(name = "category", required = false) var category: String = ""
    @field:Element(name = "starttime", required = false) var startTime: String = ""
    @field:Element(name = "prettytimes", required = false) var prettyTimes: String = ""
    @field:Element(name = "day", required = false) var day: Int? = null
}

@Root(name = "resources", strict = false)
class ResourcesDto {
    @field:Element(name = "module", required = false) var modules: ModuleDto? = null
    @field:Element(name = "staff", required = false) var staffs: StaffDto? = null
    @field:Element(name = "group", required = false) var groups: GroupDto? = null
    @field:Element(name = "room", required = false) var rooms: RoomDto? = null
}

@Root(name = "staff", strict = false)
class StaffDto {
    @field:ElementList(name = "item", inline = true, required = false) var items: List<String> = ArrayList()
}

@Root(name = "module", strict = false)
class ModuleDto {
    @field:ElementList(name = "item", inline = true, required = false) var items: List<String> = ArrayList()
}

@Root(name = "group", strict = false)
class GroupDto {
    @field:ElementList(name = "item", inline = true, required = false) var items: List<String> = ArrayList()
}

@Root(name = "room", strict = false)
class RoomDto {
    @field:ElementList(name = "item", inline = true, required = false) var items: List<String> = ArrayList()
}