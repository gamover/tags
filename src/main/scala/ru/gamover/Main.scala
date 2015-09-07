package ru.gamover

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */
object Main extends App {
  val storage = new Storage()
  val server = new Server(storage)
  val client = new Client(server)

  val idEntryOne   = client.addEntry("one entry")
  val idEntryTwo   = client.addEntry("two entry")
  val idEntryThree = client.addEntry("three entry")

  val idTagOne   = client.addTag("one tag")
  val idTagTwo   = client.addTag("two tag")
  val idTagThree = client.addTag("three tag")

  client.addEntryTag(idEntryOne, idTagOne)
  client.addEntryTag(idEntryOne, idTagTwo)
  client.addEntryTag(idEntryOne, idTagThree)

  client.addEntryTag(idTagTwo, idTagOne)
  client.addEntryTag(idTagTwo, idTagTwo)
  client.addEntryTag(idTagTwo, idTagThree)

  client.addEntryTag(idTagThree, idTagOne)
  client.addEntryTag(idTagThree, idTagTwo)
  client.addEntryTag(idTagThree, idTagThree)

  client.removeTagFromEntry(idEntryOne, idTagOne)
  client.removeTagFromEntry(idTagTwo, idTagTwo)
  client.removeTagFromEntry(idTagThree, idTagThree)

  val tagsEntryOne   = client.getTagsByEntryId(idEntryOne)
  val tagsEntryTwo   = client.getTagsByEntryId(idEntryTwo)
  val tagsEntryThree = client.getTagsByEntryId(idEntryThree)

  val a = 1
}
