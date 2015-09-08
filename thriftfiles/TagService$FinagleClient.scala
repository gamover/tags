/**
 * Generated by Scrooge
 *   version: 4.0.0
 *   rev: 2d9d7656d3b3b7eff89450ac6a78f12af6cc627b
 *   built at: 20150828-134351
 */
package ru.gamover.tagservice.thriftscala

import com.twitter.finagle.{SourcedException, Service}
import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.{Protocols, ThriftClientRequest}
import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import com.twitter.util.{Future, Return, Throw, Throwables}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import scala.collection.{Map, Set}

import scala.language.higherKinds


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class TagService$FinagleClient(
    val service: Service[ThriftClientRequest, Array[Byte]],
    val protocolFactory: TProtocolFactory = Protocols.binaryFactory(),
    val serviceName: String = "TagService",
    stats: StatsReceiver = NullStatsReceiver
) extends TagService[Future] {
  import TagService._

  protected def encodeRequest(name: String, args: ThriftStruct) = {
    val buf = new TMemoryBuffer(512)
    val oprot = protocolFactory.getProtocol(buf)

    oprot.writeMessageBegin(new TMessage(name, TMessageType.CALL, 0))
    args.write(oprot)
    oprot.writeMessageEnd()

    val bytes = Arrays.copyOfRange(buf.getArray, 0, buf.length)
    new ThriftClientRequest(bytes, false)
  }

  protected def decodeResponse[T <: ThriftStruct](resBytes: Array[Byte], codec: ThriftStructCodec[T]) = {
    val iprot = protocolFactory.getProtocol(new TMemoryInputTransport(resBytes))
    val msg = iprot.readMessageBegin()
    try {
      if (msg.`type` == TMessageType.EXCEPTION) {
        val exception = TApplicationException.read(iprot) match {
          case sourced: SourcedException =>
            if (serviceName != "") sourced.serviceName = serviceName
            sourced
          case e => e
        }
        throw exception
      } else {
        codec.decode(iprot)
      }
    } finally {
      iprot.readMessageEnd()
    }
  }

  protected def missingResult(name: String) = {
    new TApplicationException(
      TApplicationException.MISSING_RESULT,
      name + " failed: unknown result"
    )
  }

  protected def setServiceName(ex: Throwable): Throwable =
    if (this.serviceName == "") ex
    else {
      ex match {
        case se: SourcedException =>
          se.serviceName = this.serviceName
          se
        case _ => ex
      }
    }

  // ----- end boilerplate.

  private[this] val scopedStats = if (serviceName != "") stats.scope(serviceName) else stats
  private[this] object __stats_addEntry {
    val RequestsCounter = scopedStats.scope("addEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("addEntry").counter("success")
    val FailuresCounter = scopedStats.scope("addEntry").counter("failures")
    val FailuresScope = scopedStats.scope("addEntry").scope("failures")
  }
  
  def addEntry(name: String): Future[Long] = {
    __stats_addEntry.RequestsCounter.incr()
    this.service(encodeRequest("addEntry", AddEntry.Args(name))) flatMap { response =>
      val result = decodeResponse(response, AddEntry.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("addEntry"))
    } respond {
      case Return(_) =>
        __stats_addEntry.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_addEntry.FailuresCounter.incr()
        __stats_addEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_removeEntry {
    val RequestsCounter = scopedStats.scope("removeEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("removeEntry").counter("success")
    val FailuresCounter = scopedStats.scope("removeEntry").counter("failures")
    val FailuresScope = scopedStats.scope("removeEntry").scope("failures")
  }
  
  def removeEntry(id: Long): Future[Unit] = {
    __stats_removeEntry.RequestsCounter.incr()
    this.service(encodeRequest("removeEntry", RemoveEntry.Args(id))) flatMap { response =>
      val result = decodeResponse(response, RemoveEntry.Result)
      val exception: Future[Nothing] =
        null
  
      if (exception != null) exception else Future.Done
    } respond {
      case Return(_) =>
        __stats_removeEntry.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_removeEntry.FailuresCounter.incr()
        __stats_removeEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_getEntryById {
    val RequestsCounter = scopedStats.scope("getEntryById").counter("requests")
    val SuccessCounter = scopedStats.scope("getEntryById").counter("success")
    val FailuresCounter = scopedStats.scope("getEntryById").counter("failures")
    val FailuresScope = scopedStats.scope("getEntryById").scope("failures")
  }
  
  def getEntryById(id: Long): Future[Seq[ru.gamover.tagservice.thriftscala.Entry]] = {
    __stats_getEntryById.RequestsCounter.incr()
    this.service(encodeRequest("getEntryById", GetEntryById.Args(id))) flatMap { response =>
      val result = decodeResponse(response, GetEntryById.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("getEntryById"))
    } respond {
      case Return(_) =>
        __stats_getEntryById.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_getEntryById.FailuresCounter.incr()
        __stats_getEntryById.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_getEntriesByTags {
    val RequestsCounter = scopedStats.scope("getEntriesByTags").counter("requests")
    val SuccessCounter = scopedStats.scope("getEntriesByTags").counter("success")
    val FailuresCounter = scopedStats.scope("getEntriesByTags").counter("failures")
    val FailuresScope = scopedStats.scope("getEntriesByTags").scope("failures")
  }
  
  def getEntriesByTags(tagIdList: Seq[Long] = Seq[Long]()): Future[Seq[ru.gamover.tagservice.thriftscala.Entry]] = {
    __stats_getEntriesByTags.RequestsCounter.incr()
    this.service(encodeRequest("getEntriesByTags", GetEntriesByTags.Args(tagIdList))) flatMap { response =>
      val result = decodeResponse(response, GetEntriesByTags.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("getEntriesByTags"))
    } respond {
      case Return(_) =>
        __stats_getEntriesByTags.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_getEntriesByTags.FailuresCounter.incr()
        __stats_getEntriesByTags.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_addTag {
    val RequestsCounter = scopedStats.scope("addTag").counter("requests")
    val SuccessCounter = scopedStats.scope("addTag").counter("success")
    val FailuresCounter = scopedStats.scope("addTag").counter("failures")
    val FailuresScope = scopedStats.scope("addTag").scope("failures")
  }
  
  def addTag(name: String): Future[Long] = {
    __stats_addTag.RequestsCounter.incr()
    this.service(encodeRequest("addTag", AddTag.Args(name))) flatMap { response =>
      val result = decodeResponse(response, AddTag.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("addTag"))
    } respond {
      case Return(_) =>
        __stats_addTag.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_addTag.FailuresCounter.incr()
        __stats_addTag.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_removeTag {
    val RequestsCounter = scopedStats.scope("removeTag").counter("requests")
    val SuccessCounter = scopedStats.scope("removeTag").counter("success")
    val FailuresCounter = scopedStats.scope("removeTag").counter("failures")
    val FailuresScope = scopedStats.scope("removeTag").scope("failures")
  }
  
  def removeTag(id: Long): Future[Unit] = {
    __stats_removeTag.RequestsCounter.incr()
    this.service(encodeRequest("removeTag", RemoveTag.Args(id))) flatMap { response =>
      val result = decodeResponse(response, RemoveTag.Result)
      val exception: Future[Nothing] =
        null
  
      if (exception != null) exception else Future.Done
    } respond {
      case Return(_) =>
        __stats_removeTag.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_removeTag.FailuresCounter.incr()
        __stats_removeTag.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_getTagById {
    val RequestsCounter = scopedStats.scope("getTagById").counter("requests")
    val SuccessCounter = scopedStats.scope("getTagById").counter("success")
    val FailuresCounter = scopedStats.scope("getTagById").counter("failures")
    val FailuresScope = scopedStats.scope("getTagById").scope("failures")
  }
  
  def getTagById(id: Long): Future[Seq[ru.gamover.tagservice.thriftscala.Tag]] = {
    __stats_getTagById.RequestsCounter.incr()
    this.service(encodeRequest("getTagById", GetTagById.Args(id))) flatMap { response =>
      val result = decodeResponse(response, GetTagById.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("getTagById"))
    } respond {
      case Return(_) =>
        __stats_getTagById.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_getTagById.FailuresCounter.incr()
        __stats_getTagById.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_getEntryTags {
    val RequestsCounter = scopedStats.scope("getEntryTags").counter("requests")
    val SuccessCounter = scopedStats.scope("getEntryTags").counter("success")
    val FailuresCounter = scopedStats.scope("getEntryTags").counter("failures")
    val FailuresScope = scopedStats.scope("getEntryTags").scope("failures")
  }
  
  def getEntryTags(entryId: Long): Future[Seq[ru.gamover.tagservice.thriftscala.Tag]] = {
    __stats_getEntryTags.RequestsCounter.incr()
    this.service(encodeRequest("getEntryTags", GetEntryTags.Args(entryId))) flatMap { response =>
      val result = decodeResponse(response, GetEntryTags.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("getEntryTags"))
    } respond {
      case Return(_) =>
        __stats_getEntryTags.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_getEntryTags.FailuresCounter.incr()
        __stats_getEntryTags.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_addTagToEntry {
    val RequestsCounter = scopedStats.scope("addTagToEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("addTagToEntry").counter("success")
    val FailuresCounter = scopedStats.scope("addTagToEntry").counter("failures")
    val FailuresScope = scopedStats.scope("addTagToEntry").scope("failures")
  }
  
  def addTagToEntry(entryId: Long, tagId: Long): Future[Long] = {
    __stats_addTagToEntry.RequestsCounter.incr()
    this.service(encodeRequest("addTagToEntry", AddTagToEntry.Args(entryId, tagId))) flatMap { response =>
      val result = decodeResponse(response, AddTagToEntry.Result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("addTagToEntry"))
    } respond {
      case Return(_) =>
        __stats_addTagToEntry.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_addTagToEntry.FailuresCounter.incr()
        __stats_addTagToEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
  private[this] object __stats_removeTagFromEntry {
    val RequestsCounter = scopedStats.scope("removeTagFromEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("removeTagFromEntry").counter("success")
    val FailuresCounter = scopedStats.scope("removeTagFromEntry").counter("failures")
    val FailuresScope = scopedStats.scope("removeTagFromEntry").scope("failures")
  }
  
  def removeTagFromEntry(entryId: Long, tagId: Long): Future[Unit] = {
    __stats_removeTagFromEntry.RequestsCounter.incr()
    this.service(encodeRequest("removeTagFromEntry", RemoveTagFromEntry.Args(entryId, tagId))) flatMap { response =>
      val result = decodeResponse(response, RemoveTagFromEntry.Result)
      val exception: Future[Nothing] =
        null
  
      if (exception != null) exception else Future.Done
    } respond {
      case Return(_) =>
        __stats_removeTagFromEntry.SuccessCounter.incr()
      case Throw(ex) =>
        setServiceName(ex)
        __stats_removeTagFromEntry.FailuresCounter.incr()
        __stats_removeTagFromEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
    }
  }
}
