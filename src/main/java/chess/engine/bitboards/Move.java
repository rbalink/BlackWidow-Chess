package main.java.chess.engine.bitboards;

public class Move {

	final int currentLocation;
	final int destinationLocation;
	final BitBoard.Piece movedPiece;

	public Move(final int currentLocation,
                final int destinationLocation,
                final BitBoard.Piece moved) {
		this.currentLocation = currentLocation;
		this.destinationLocation = destinationLocation;
		this.movedPiece = moved;
	}

	@Override
	public String toString() {
		return BitBoard.getPositionAtCoordinate(this.currentLocation) + "-"
		        + BitBoard.getPositionAtCoordinate(this.destinationLocation);
	}

	@Override
	public int hashCode() {
		return this.movedPiece.hashCode() +
			   this.currentLocation +
               this.destinationLocation;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return (this.movedPiece == otherMove.getMovedPiece())
		        && (this.currentLocation == otherMove.getCurrentLocation())
		        && (this.destinationLocation == otherMove.getDestinationLocation());
	}

	public int getDestinationLocation() {
		return this.destinationLocation;
	}

	public int getCurrentLocation() {
		return this.currentLocation;
	}

	public BitBoard.Piece getMovedPiece() {
		return this.movedPiece;
	}

}
