package ru.gamover

import scala.collection.mutable.ArrayBuffer

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */
class Server(storage: Storage) {
  def addEntry(name: String): Long = storage.addEntry(name)
  def removeEntry(id: Long): Unit = storage.removeEntry(id)
  def getEntryById(id: Long): Option[Entry] = storage.getEntryById(id)

  def addTag(name: String): Long = storage.addTag(name)
  def removeTag(id: Long): Unit = storage.removeTag(id)
  def getTagById(id: Long): Option[Tag] = storage.getTagById(id)
  def getTagsByEntryId(entryId: Long): List[Tag] = storage.getTagsByEntryId(entryId)

  def addEntryTag(entryId: Long, tagId: Long): Long = storage.addEntryTag(entryId, tagId)
  def removeEntryTagById(id: Long): Unit = storage.removeEntryTagById(id)
  def removeTagFromEntry(entryId: Long, tagId: Long): Unit = storage.removeTagFromEntry(entryId: Long, tagId: Long)
}