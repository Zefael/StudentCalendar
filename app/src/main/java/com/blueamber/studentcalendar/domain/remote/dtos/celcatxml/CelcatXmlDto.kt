package com.blueamber.studentcalendar.domain.remote.dtos.celcatxml

import org.simpleframework.xml.*

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
    @field:Element(name = "notes", required = false) var notes: String = ""
}

@Root(name = "resources", strict = false)
class ResourcesDto {
    @field:ElementList(name = "module", entry = "item", required = false, type = String::class) var modules: List<String> = ArrayList()
    @field:ElementList(name = "staff", entry = "item", required = false, type = String::class) var staffs: List<String> = ArrayList()
    @field:ElementList(name = "group", entry = "item", required = false, type = String::class) var groups: List<String> = ArrayList()
    @field:ElementList(name = "room", entry = "item", required = false, type = String::class) var rooms: List<String> = ArrayList()
}
