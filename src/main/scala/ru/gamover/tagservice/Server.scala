package ru.gamover.tagservice

import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import ru.gamover.tagservice.thriftscala.TagService

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */

/**
 * Реализация интерфейса thrift-сервиса
 * @param storage хранилище
 */
class TagServiceImpl(storage: Storage) extends TagService[Future] {
  import ru.gamover.tagservice.Storage._

  /**
   * Добавление записи
   * @param name имя записи
   * @return
   */
  override def addEntry(name: String): Future[Long] = Future.value {
    storage.addEntry(name)
  }

  /**
   * Удаление записи
   * @param id идентификатор записи
   * @return
   */
  override def removeEntry(id: Long): Future[Unit] = Future.value {
    storage.removeEntry(id)
  }

  /**
   * Получение записи по идентификтору
   * @param id идентификатор записи
   * @return
   */
  override def getEntryById(id: Long): Future[Seq[thriftscala.Entry]] = Future.value {
    storage.getEntryById(id) match {
      case Some(entry) => List(entry)
      case _ => List.empty
    }
  }

  /**
   * Получение списка записей по списку идентификаторов тегов
   * @param tagIdList список идентификаторов тегов
   * @return
   */
  override def getEntriesByTags(tagIdList: Seq[Long]): Future[Seq[thriftscala.Entry]] = Future.value {
    storage.getEntriesByTags(tagIdList.toList)
  }

  /**
   * Добавление тега
   * @param name имя тега
   * @return
   */
  override def addTag(name: String): Future[Long] = Future.value {
    storage.addTag(name)
  }

  /**
   * Удаление тега
   * @param id идентификатор тега
   * @return
   */
  override def removeTag(id: Long): Future[Unit] = Future.value {
    storage.removeTag(id)
  }

  /**
   * Получение тега по идентификатору
   * @param id идентификатор тега
   * @return
   */
  override def getTagById(id: Long): Future[Seq[thriftscala.Tag]] = Future.value {
    storage.getTagById(id) match {
      case Some(tag) => List(tag)
      case _ => List.empty
    }
  }

  /**
   * Получение списка тегов записи
   * @param entryId идентификатор записи
   * @return
   */
  override def getEntryTags(entryId: Long): Future[Seq[thriftscala.Tag]] = Future.value {
    storage.getTagsByEntryId(entryId)
  }

  /**
   * Добавление тега к записи
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   * @return
   */
  override def addTagToEntry(entryId: Long, tagId: Long): Future[Long] = Future.value {
    storage.addEntryTag(entryId, tagId)
  }

  /**
   * Удаление тега от записи
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   * @return
   */
  override def removeTagFromEntry(entryId: Long, tagId: Long): Future[Unit] = Future.value {
    storage.removeTagFromEntry(entryId: Long, tagId: Long)
  }
}

object Server extends App {
  val server = Thrift.serveIface("localhost:8080", new TagServiceImpl(new Storage))
  Await.ready(server)
}