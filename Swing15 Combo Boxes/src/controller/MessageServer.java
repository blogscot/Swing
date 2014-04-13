package controller;

import gui.Message;

import java.util.*;

/*
 * This is a simulated server
 */

public class MessageServer implements Iterable<Message> {
	private Map<Integer, List<Message>> messages;
	private List<Message> selected;

	public MessageServer() {

		selected = new ArrayList<>();
		messages = new TreeMap<>();

		List<Message> list = new ArrayList<>();
		list.add(new Message("The cat is missing.",
				"Have you seen Felix anywhere?"));
		list.add(new Message("See you later?",
				"Are we still meeting down the pub?"));
		messages.put(0, list);

		list = new ArrayList<>();
		list.add(new Message("The dog is missing.",
				"Have you seen Baggy anywhere?"));
		list.add(new Message("See you later?",
				"Are we still meeting down the gym?"));
		messages.put(1, list);
	}

	public void setSelectedServers(Set<Integer> servers) {

		selected.clear();
		for (Integer id : servers) {
			if (messages.containsKey(id)) {
				selected.addAll(messages.get(id));
			}
		}
	}

	public Integer getMessagesCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}

	private Iterator<Message> iterator;

	class MessageIterator implements Iterator<Message> {

		public MessageIterator(List<Message> messages) {
			iterator = messages.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Message next() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {/* do nothing */}

			return iterator.next();
		}

		@Override
		public void remove() {
			iterator.remove();
		}
	}
}
