package queryRunners;

import Exceptions.NonUpdatableView;
import queryParsers.parsed.Parsed;

/**
 * Created by user on 26/01/16 AD.
 */
public abstract class QueryRunner<P extends Parsed> {
	public abstract void run(P parsedQuery, boolean log) throws NonUpdatableView;
}
