/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Thomas Denoréaz
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.sun.jna._

import cwiid._
import cwiid.CwiidLibrary._

object CWiid extends App {

	def str2bdaddr(addr: String) = addr.split(":").map(Integer.valueOf(_, 16).toByte).reverse.toArray

	val errorCallback = new cwiid_err_t {
		def apply(wm: Pointer, str: Pointer, ap: va_list): Unit = {
			println("error callback")
		}
	}

	val callback = new cwiid_mesg_callback_t {
		def apply(wm: Pointer, msg_count: Int, mesg: Pointer, timestamp: timespec): Unit = {
			println("callback")
		}
	}

	val addr = "00:00:00:00:00:00" // Discover ANY bluetooth device

	val bdaddr = new bdaddr_t(str2bdaddr(addr))

	val cwiid = CwiidLibrary.INSTANCE

	println("Press buttons 1 + 2...")
	Thread.sleep(3000l)

	import cwiid._

	cwiid_set_err(errorCallback)

	println("Trying to connect to " + addr)
	val wiimote = cwiid_open(bdaddr, 0)
	if (wiimote == null) println("\tError while trying to connect.")
	else {
		println("\tSuccessfully connected.")

		println("Assigning callback...")
		cwiid_set_mesg_callback(wiimote, callback)

		cwiid_enable(wiimote, CWIID_FLAG_MESG_IFC)
		cwiid_set_rpt_mode(wiimote, CWIID_RPT_BTN.toByte)

		println("Trying to switch on LEDs")
		val led = cwiid_set_led(wiimote, 0xAA.toByte)
		if (led == 0) println("\tSuccessfully switched the LEDs");
		else println("\tError setting LEDs with error code " + led);

		Thread.sleep(5000l)

		println("Trying to disconnect from " + addr)
		val error = cwiid_close(wiimote)
		if (error == 0) println("\tSuccessfully disconnected.")
		else println("\tError while disconnecting with error code " + error)
	}
}
