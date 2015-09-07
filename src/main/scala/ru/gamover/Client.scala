package ru.gamover

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */
class Client(server: Server) {
  def addEntry(name: String): Long = server.addEntry(name)
  def removeEntry(id: Long): Unit = server.removeEntry(id)
  def getEntryById(id: Long): Option[Entry] = server.getEntryById(id)

  def addTag(name: String): Long = server.addTag(name)
  def removeTag(id: Long): Unit = server.removeTag(id)
  def getTagById(id: Long): Option[Tag] = server.getTagById(id)
  def getTagsByEntryId(entryId: Long): List[Tag] = server.getTagsByEntryId(entryId)

  def addEntryTag(entryId: Long, tagId: Long): Long = server.addEntryTag(entryId, tagId)
  def removeEntryTagById(id: Long): Unit = server.removeEntryTagById(id)
  def removeTagFromEntry(entryId: Long, tagId: Long): Unit = server.removeTagFromEntry(entryId: Long, tagId: Long)
}
