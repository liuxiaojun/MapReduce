/**
 * Created by liuxiaojun on 15/9/29.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobMain {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(configuration);
        fs.delete(new Path(args[1]), true);

        Job job = new Job(configuration,"uniquejob");
        job.setJarByClass(JobMain.class);

        job.setMapperClass(IntSortMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(IntSortReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        System.exit(success?0:1);
    }
}
